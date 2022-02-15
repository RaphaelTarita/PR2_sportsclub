package a11730648;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class SportsClub {
    private static final String NOT_A_MEMBER = " is not a member in the Sports Club";
    private final String name;
    private final BigDecimal feePerSports;
    private final Set<Member> members = new LinkedHashSet<>();
    private final Map<Sports, Set<Trainer>> offeredSports = new LinkedHashMap<>();

    public SportsClub(String name, BigDecimal feePerSports) {
        if (name == null || name.isEmpty() || feePerSports == null) {
            throw new IllegalArgumentException("name cannot be null or empty, and feePerSports cannot be null");
        }

        this.name = name;
        this.feePerSports = feePerSports;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getFeePerSports() {
        return feePerSports;
    }

    public Set<Member> getMembers() {
        return new HashSet<>(members);
    }

    public Set<Sports> getSports() {
        return offeredSports.keySet();
    }

    public BigDecimal calculateMembershipFee(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("Cannot calculate membership fee for null value");
        }
        if (!members.contains(member)) {
            throw new IllegalArgumentException(member.getName() + NOT_A_MEMBER);
        }

        Set<Sports> possible = member.getBillableSports();
        possible.retainAll(offeredSports.keySet());

        return possible.stream()
            .map(s -> s.getFee(feePerSports))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean registerSports(Member member, Sports sports, Level level) {
        if (member == null || sports == null || level == null) {
            throw new IllegalArgumentException("Member, Sports or Level cannot be null");
        }
        if (!members.contains(member)) {
            throw new IllegalArgumentException(member.getName() + NOT_A_MEMBER);
        }

        Set<Trainer> offer = offeredSports.get(sports);
        if (
            offer == null
                || offer.stream().noneMatch(t -> t.getAccreditations().get(sports).ordinal() >= level.ordinal())
        ) {
            return false;
        }
        Level learned = member.learn(sports, level);
        return learned == level;
    }

    public boolean addMember(Member member) {
        if (member instanceof Trainer) {
            Trainer trainer = (Trainer) member;
            for (Map.Entry<Sports, Level> accs : trainer.getAccreditations().entrySet()) {
                offeredSports.computeIfAbsent(accs.getKey(), s -> new HashSet<>());
                offeredSports.get(accs.getKey()).add(trainer);
            }
        }
        if (member == null) {
            throw new IllegalArgumentException("Cannot add null value as member");
        }

        return members.add(member);
    }

    public boolean removeMember(Member member) {
        if (member instanceof Trainer) {
            Trainer trainer = (Trainer) member;
            for (Map.Entry<Sports, Set<Trainer>> entry : offeredSports.entrySet()) {
                entry.getValue().remove(trainer);
                if (entry.getValue().isEmpty()) {
                    offeredSports.remove(entry.getKey());
                }
            }
        }

        if (member != null) {
            return members.remove(member);
        } else {
            throw new IllegalArgumentException("Cannot remove null value from members");
        }
    }

    @Override
    public String toString() {
        return "SportsClub[name: "
            + name
            + ", feePerSports: "
            + feePerSports.toString()
            + ", offeredSports: "
            + offeredSports.toString()
            + ']';
    }
}
